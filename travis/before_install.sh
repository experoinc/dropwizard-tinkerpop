#!/usr/bin/env bash

if [ "${TRAVIS_BRANCH}" = 'master' ] && [ "${TRAVIS_PULL_REQUEST}" == 'false' ]; then
  openssl aes-256-cbc -K ${encrypted_d8b618a76e1d_key} \
    -iv ${encrypted_d8b618a76e1d_iv} \
    -in codesigning.asc.enc \
    -out codesigning.asc -d

    gpg --fast-import codesigning.asc

  echo "public keys"
  gpg --list-keys
  echo "private keys"
  gpg --list-secret-keys
  if [ -z "${GPG_PASSPHRASE}" ]; then
    echo "empty pass"
    else
    echo "present pass"
  fi
    echo "attempting signature"
    gpg --batch --yes --no-tty -u ${GPG_KEY_NAME} --output test.out --passphrase "${GPG_PASSPHRASE}" --sign test.txt
    echo "attempting signature2"
    echo "${GPG_PASSPHRASE}" | gpg --batch --yes --no-tty -u ${GPG_KEY_NAME} --output test.out --passphrase-fd 0 --sign test.txt
    echo "finishing signature"

    echo "attempting decrypt"
    gpg -u ${GPG_KEY_NAME} --output test.dec --decrypt test.out
    echo "finishing decrypt"
    ls -alh
    cat test.dec
fi