The following is a set of guidelines about making contributions to kotoframework. Please spend several minutes in reading these guidelines before submitting an issue or pull request.

## Code of Conduct

We have adopted a [Code of Conduct](https://github.com/kotoframework/kotoframework/blob/master/CODE_OF_CONDUCT.md) that we expect every contributor to adhere to. Please look through each section carefully so that you can understand what actions will and will not be allowed.

## Open Development

All work on kotoframework happens directly on [GitHub](https://github.com/kotoframework/kotoframework). Both core team members and external contributors go through the same reviewing process in order to submit a pull request.

## Bugs

We are using [GitHub Issues](https://github.com/kotoframework/kotoframework/issues) for bug tracing. The best way to get your bug fixed is using our [issue helper](https://ng.ant.design/issue-helper/#/en) and provide a reproduction following this [template](https://stackblitz.com/edit/kotoframework-ivy).

Before reporting a new bug, please make sure you have filtered existing issues, and read our [FAQ](docs/faq/en).

## Your First Pull Request

Please use the following resources for your first pull request:

* [How to Contribute to Open Source](https://opensource.guide/how-to-contribute/)
* [First Contributions](https://github.com/firstcontributions/first-contributions)

To help you get your feet wet and get familiar with our contribution process, we have a list of [Good First Issue](https://github.com/kotoframework/kotoframework/labels/good%20first%20issue) that contain either bugs that are relatively easy to be fixed or small features. These issues are considered as great starting points.

If you decide to handle an issue, please be sure to check the comment thread in case someone else has already been working on it. If nobody is working on it at the moment, please leave a comment stating that you intend to work on it, preventing others from doing duplicate work.

If somebody claimed an issue without any follow up for more than two weeks, it should be fine to take over it. Nevertheless, be sure to leave a comment under such issues.

## Contribute

The core team is monitoring all pull requests. Your pull request will be either successfully merged, requested for a change, or closed with a reasonable explanation.

**Before submitting a pull request**, please make sure to follow the steps below:

1. Run `npm install` in the repository root.
2. If you have fixed a bug or added a feature that should be tested, please add test cases!
3. Make sure the test suite passes (`npm run test`).
4. Make sure your code lints (`npm run lint`).
5. Make sure rebase your code to keep the history clean.
6. Make sure your commit message meet the requirements of [guidelines](https://github.com/kotoframework/kotoframework/blob/master/CONTRIBUTING.md#-commit-message-guidelines)

## How to Submit a Pull Request

1. Fork the repository of `kotoframework`. The later steps must be done on the forked repository
2. On `master`: `git remote add upstream https://github.com/kotoframework/kotoframework.git`
3. On `master`: `git pull upstream master`
4. On `master`: `git push origin master`
5. Checkout to the feature branch (for example, if the branch is called `docs-fix`): `git checkout docs-fix`
6. On `docs-fix` rebase on master: `git rebase origin/master`
7. On `docs-fix` resolve codes and commit: `git commit -a`, you need to follow the [commit message guidelines](https://github.com/kotoframework/kotoframework/blob/master/CONTRIBUTING.md#-commit-message-guidelines)
8. Then, push up: `git push` (might need `-f`, just be sure you understand force pushing before you do it)
9. Submit a Pull Request on the Github

## Development Workflow

After cloning `kotoframework` and running `npm install` to install its dependencies, you can also run the following commands:

* `npm start` runs kotoframework website locally.
* `npm run lint` checks the code style.
* `npm test` runs the complete test suite.
* `npm run test:watch [name]` run some test files and monitor changes.
* `npm run build:lib` creates a build of `kotoframework` under `publish` directory.

If you encounter problems while developing `kotoframework`, please refer to our [development guide](https://github.com/kotoframework/kotoframework/wiki/Development-Guide) on the GitHub.
