#!/bin/bash

file=$1

# Script used to read Property File
FILE_NAME=$1

# Key in Property File
#key="title"

# Variable to hold the Property Value
prop_value=""

if [ -f "$file" ]
then
  echo "$file found."

while IFS='=' read -r key value
  do
	key=$(echo $key)

        prop_value=`cat ${FILE_NAME} | grep ${key} | cut -d'=' -f2`

	echo "Key = ${key} ; Value = " ${prop_value}
done < "$file"

else
  echo "$file not found."
fi
