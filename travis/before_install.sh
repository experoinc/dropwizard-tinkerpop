#!/usr/bin/env bash

set -e
if [ "${TRAVIS_BRANCH}" = 'master' ] && [ "${TRAVIS_PULL_REQUEST}" == 'false' ]; then
  openssl aes-256-cbc -K ${encrypted_d8b618a76e1d_key} \
    -iv ${encrypted_d8b618a76e1d_iv} \
    -in codesigning.asc.enc \
    -out codesigning.asc -d

    gpg --fast-import codesigning.asc

    echo "attempting signature"
    echo ${GPG_PASSPHRASE} | gpg --no-tty -u ${GPG_KEY_NAME} --output test.out --passphrase-fd 0 --sign test.txt
    echo "finishing signature"

    echo "attempting decrypt"
    gpg -u ${GPG_KEY_NAME} --output test.dec --decrypt test.out
    echo "finishing decrypt"
    ls -alh
    cat test.dec
fi