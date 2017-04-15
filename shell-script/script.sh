#!/bin/bash

# Script used to read input files
input_html_file=$0
property_file=$1
output_html_file=$2

# Key in Property File
title=""

# Variable to hold the Property Value
environment=""

# Read the Property Value
if [ -f "$property_file" ]
then  
     while IFS='=' read -r key value
     do
	prop_key=$(echo $key)

        prop_value=`cat ${property_file} | grep ${prop_key} | cut -d'=' -f2`
	echo "Key = ${prop_key} ; Value = " ${prop_value}
	prop_value=``	
     done < "$property_file"
   else
    echo "$property_file not found."
fi

# Parse input html file
if [ -f "$input_html_file" ]
then
    sed -e s/[[title]]}/${title}/g index.html > output.html
else
echo "$input_html_file not found."
fi


