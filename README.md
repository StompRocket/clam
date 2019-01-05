# clam

A program to easily write servers.

## Installation

Download from releases page.

## Usage

Clam is a CLI that acts as a web server, which abstracts the process of
routing to work with any language, without the need to use a specific
library. It uses a configuration file `.clam.yml` which provides
information such as what port to serve on, and what commands to run at
what routes.

Here is what the most basic clam config looks like:

```yaml

port: 8000
routes:
	- path: /
      command: echo "Routed to /"
    - path: /user/*
      command: python get_user.py $clam-path

```

Running `clam` in a directory with this file will start a web server on port
8000, and start listening for events. 

    $ java -jar clam-0.1.0-standalone.jar [args]

## Options

-h: help

new: new .clam.yml

## License

Copyright © 2019 StompRocket

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
