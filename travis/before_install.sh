#!/usr/bin/env bash

if [ "${TRAVIS_BRANCH}" = 'master' ] && [ "${TRAVIS_PULL_REQUEST}" == 'false' ]; then
  openssl aes-256-cbc -K ${encrypted_d8b618a76e1d_key} \
    -iv ${encrypted_d8b618a76e1d_iv} \
    -in codesigning.asc.enc \
    -out codesigning.asc -d

    gpg --fast-import codesigning.asc

    gpg --output test.out --passphrase ${GPG_PASSPHRASE} --sign test.txt
    gpg --output test.dec --decrypt test.out
    ls -alh
    cat test.dec
fi