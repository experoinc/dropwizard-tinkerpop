#!/usr/bin/env bash

if [ "${TRAVIS_BRANCH}" = 'master' ] && [ "${TRAVIS_PULL_REQUEST}" == 'false' ]; then
  openssl aes-256-cbc -K ${encrypted_d8b618a76e1d_key} \
    -iv ${encrypted_d8b618a76e1d_iv} \
    -in codesigning.asc.enc \
    -out codesigning.asc -d

  mkdir -p ${HOME}/.gpg
  KEYRINGS="--keyring ${HOME}/.gpg/pubring.gpg "
  KEYRINGS+="--secret-keyring ${HOME}/.gpg/secring.gpg "
  KEYRINGS+="--primary-keyring ${HOME}/.gpg/pubring.gpg "
  KEYRINGS+="--no-default-keyring "

  echo "gpg version"
  gpg --version

  gpg ${KEYRINGS} --batch --yes --fast-import codesigning.asc

  echo "public keys"
  gpg ${KEYRINGS} --list-keys
  echo "private keys"
  gpg ${KEYRINGS} --list-secret-keys
  if [ -z "${GPG_PASSPHRASE}" ]; then
    echo "empty pass"
    else
    echo "present pass"
  fi
    echo "attempting signature"

    gpg ${KEYRINGS} \
      --batch --yes --no-tty -u ${GPG_KEY_NAME} \
      --output test.out \
      --no-use-agent \
      --passphrase "${GPG_OTHER_VAR}" \
      --sign test.txt

    gpg ${KEYRINGS} \
      --batch --yes --no-tty -u ${GPG_KEY_NAME} \
      --output test.out \
      --no-use-agent \
      --passphrase "${GPG_PASSPHRASE}" \
      --sign test.txt

    echo "attempting signature 2"

    echo "${GPG_PASSPHRASE}" | gpg --batch --yes --no-tty \
      ${KEYRINGS} \
      -u ${GPG_KEY_NAME} --output test.out \
      --passphrase-fd 0 \
      --no-use-agent \
      --sign test.txt

    echo "finishing signature"

    echo "attempting decrypt"
    gpg ${KEYRINGS} -u ${GPG_KEY_NAME} --output test.dec --decrypt test.out
    echo "finishing decrypt"
    ls -alh
    cat test.dec
fi
