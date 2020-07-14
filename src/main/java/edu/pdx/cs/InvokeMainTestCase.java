package edu.pdx.cs;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Permission;

public class InvokeMainTestCase {

    public InvokeMainTestCase() {
    }

    protected InvokeMainTestCase.MainMethodResult invokeMain(Class mainClass, String... args) {
        return (new InvokeMainTestCase.MainMethodResult(mainClass, args)).invoke();
    }

    protected class MainMethodResult {
        private final Class mainClass;
        private final String[] args;
        private Integer exitCode;
        private String out;
        private String err;

        MainMethodResult(Class mainClass, String[] args) {
            this.mainClass = mainClass;
            this.args = args;
        }

        public InvokeMainTestCase.MainMethodResult invoke() {
            Method main;
            try {
                main = this.mainClass.getMethod("main", String[].class);
            } catch (NoSuchMethodException var5) {
                throw new IllegalArgumentException("Class " + this.mainClass.getName() + " does not have a main method");
            }

            try {
                this.invokeMain(main);
                return this;
            } catch (IllegalAccessException var3) {
                throw new IllegalArgumentException("Cannot invoke main method of " + this.mainClass.getName(), var3);
            } catch (InvocationTargetException var4) {
                throw new IllegalArgumentException("Error while invoking main method of " + this.mainClass.getName(), var4);
            }
        }

        private void invokeMain(Method main) throws IllegalAccessException, InvocationTargetException {
            SecurityManager oldSecurityManager = System.getSecurityManager();
            PrintStream oldOut = System.out;
            PrintStream oldErr = System.err;

            try {
                InvokeMainTestCase.MainMethodResult.ExitStatusSecurityManager essm = new InvokeMainTestCase.MainMethodResult.ExitStatusSecurityManager(oldSecurityManager);
                System.setSecurityManager(essm);
                ByteArrayOutputStream newOut = new ByteArrayOutputStream();
                ByteArrayOutputStream newErr = new ByteArrayOutputStream();
                System.setOut(new PrintStream(newOut));
                System.setErr(new PrintStream(newErr));

                try {
                    main.invoke((Object)null, this.args);
                } catch (InvocationTargetException var12) {
                    if (!(var12.getCause() instanceof InvokeMainTestCase.MainMethodResult.ExitException)) {
                        throw var12;
                    }

                    this.exitCode = ((InvokeMainTestCase.MainMethodResult.ExitException)var12.getCause()).getExitCode();
                }

                this.out = newOut.toString();
                this.err = newErr.toString();
            } finally {
                System.setSecurityManager(oldSecurityManager);
                System.setOut(oldOut);
                System.setErr(oldErr);
            }

        }

        public Integer getExitCode() {
            return this.exitCode;
        }

        public String getTextWrittenToStandardOut() {
            return this.out;
        }

        public String getTextWrittenToStandardError() {
            return this.err;
        }

        private class ExitException extends SecurityException {
            private final int exitCode;

            public ExitException(int exitCode) {
                this.exitCode = exitCode;
            }

            public int getExitCode() {
                return this.exitCode;
            }
        }

        private class ExitStatusSecurityManager extends SecurityManager {
            private final SecurityManager delegate;

            public ExitStatusSecurityManager(SecurityManager manager) {
                this.delegate = manager;
            }

            public void checkPermission(Permission perm) {
                if (this.delegate != null) {
                    this.delegate.checkPermission(perm);
                }

            }

            public void checkPermission(Permission perm, Object context) {
                if (this.delegate != null) {
                    this.delegate.checkPermission(perm, context);
                }

            }

            public void checkExit(int status) {
                if (this.delegate != null) {
                    this.delegate.checkExit(status);
                }

                throw MainMethodResult.this.new ExitException(status);
            }
        }
    }
}
