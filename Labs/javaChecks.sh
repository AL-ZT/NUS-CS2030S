#!/bin/bash
# Run java codes, perform checkstyles, and create javadocs

jar="~cs2030s/bin/checkstyle.jar -c ~cs2030s/bin/cs2030_checks.xml"

while getopts 'c:s:d:' OPTION; do
  case "$OPTION" in
    c)
      javac ${OPTARG}
      ;;
    s)
      eval java -jar $jar ${OPTARG}
      ;;
    d)
      javadoc -quiet -private -d docs ${OPTARG}
      exit 1
      ;;
  esac
done

if [ $# -ne 0 ]
  then
    javac $1
    java $1
fi
