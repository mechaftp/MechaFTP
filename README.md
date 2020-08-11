# MechaFTP

Portable Java FTP command line client created for Agile and Open Source Software at Portland State University.

![MechaFTP Demo](https://raw.githubusercontent.com/mechaftp/MechaFTP/assets/images/mechaftp-loop.gif)

## Getting started

To work with the current client, you will need to compile from source code. This project uses the Maven Wrapper to bootstrap 
dependency installation. It's executed through `mvnw` in the root project directory.

1. Clone the repo to a location of your choosing.
2. In the central directory, build the project with `./mvnw package`. You may need to add executable file permissions
to the maven wrapper `mvnw` in order for that to work.
3. Tests run as a part of the package goal. If you want to run the tests independently, execute `./mvnw test`.
3. Launch the client with the provided executable jar file `java -jar target/mechaftp-0.1.0.jar`. You must give a hostname
as a command line argument in order to connect. If the port isn't a default port, you can specify it as well:

    `java -jar target/mechaftp-0.1.0.jar <hostname> [port]`

## Usage

The project has several FTP commands implemented. As we build out the project, this section will be added to. When the
program launches, it will connect to the given hostname (and port, if given, otherwise the default FTP port will be
used). After connection, you still need to log in to the server via the login command.

1. `downloadFile <filepath>` - download the file given by `<filepath>` relative to the current working directory on
the remote server.
2. `listLocalDirectories` - list the directories within the current working directory on your local machine.
3. `listLocalFiles` - list the non-directory files within the current working directory on your local machine.
4. `listRemoteDirectory` - list the directories within the current working directory on the remote machine.
5. `listRemoteFiles` - list the non-directory files within the current working directory on the remote machine.
6. `login <username> <password>` - attempt to log in to the connected server with the `<username>` and `<password>`
combination supplied.
7. `logout` - log out of the currently connected server.
8. `quit` - exit the currently running program.
9. `uploadFile <filepath>` - upload the file given by `<filepath>` relative to the local current working directory on
your machine to the current working directory on the remote server.

## Contributing

We welcome contributions. To get started with a contribution, please *review our open issues*. After reviewing the issues,
 you may reach out to one of our principle team members for further guidance. You can assign an issue of interest to
 yourself to begin working. Please name your branches with the pattern `issue-##-<optional-description>` using dash
 separators. We are using the project board to track project status.
 
Please feel free to *open bugs or feature requests* on our issue tracker. You can add an appropriate label and set
yourself to receive notifications for any updates to the issue.

Take a *look at open Pull Requests (PRs)*. We welcome contributions and improvements to our open PRs, whether through
comments or code contributions to the PR branch.

## Code of Conduct

In lieu of adopting our own code of conduct, this project follows the [Mozilla community participation guidelines](https://www.mozilla.org/en-US/about/governance/policies/participation/).
We welcome contributions from people of all backgrounds. The project maintainers reserve the right to enforce respectful 
behavior through private communication, public warning, and/or temporary or permanent bans. Please be a decent human being.

## Legal

Copyright © 2020 Will Huiras, Evan Kent, David Kim, Yan Li, Deep Patel, Scott Rubey

This software is licensed under the terms of the [MIT license](LICENSE). See file LICENSE for more information.
