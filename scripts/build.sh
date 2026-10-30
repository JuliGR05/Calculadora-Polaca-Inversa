#!/bin/bash
set -e
cd "$(dirname "$0")/.."
mkdir -p out
javac -encoding UTF-8 -d out src/rpn/*.java test/rpn/*.java

case "${1:-test}" in
  test) java -cp out rpn.PruebasRpn ;;
  run)  java -cp out rpn.Main ;;
  *)    echo "Uso: ./scripts/build.sh [test|run]"; exit 1 ;;
esac
