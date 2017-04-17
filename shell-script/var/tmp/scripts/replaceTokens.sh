#!/bin/bash
# Created By S Nataraja

# Script used to read input files
input_html_file=$1
property_file=$2
output_html_file=$3

# Verify input parameters
if [ "$#" -lt 3 ] 
   then
		echo "Excepted Arguments:./replaceTokens.sh ../input/index.html ../conf/prod.properties ../output/output.html"
exit 1
fi

if [ "$1" != "../input/index.html" ] 
then 
	echo "Excepted First Argument:../input/index.html"
	exit 1
elif ! [[ ("$2" == "../conf/test.properties") ||  ("$2" == "../conf/prod.properties") ]]
then
	echo "Excepted second Argument:../conf/test.properties   OR  ../conf/prod.properties"
	exit 1
elif [ "$3" != "../output/output.html" ] 
then
	echo "Excepted Third Argument:../output/output.html"
	exit 1
fi


# Output Directory created if it does not exists
if ! [ -f "$output_html_file" ]
then  
    mkdir -p "../output"
fi

# declare an associative array
declare -A env_prop
# Read the Property Value
if [ -f "$property_file" ]
then  
# read file line by line and populate the array. Field separator is "="
while IFS='=' read -r k v; do
   env_prop["$k"]="$v"
done < "$property_file"
  else
    echo "$property_file not found."
fi


# Parse input html file
if [ -f "$input_html_file" ]
then
	cp /dev/null $output_html_file
	cat $input_html_file >> $output_html_file
	for i in "${!env_prop[@]}"
		do
		search=$i
		replace=${env_prop[$i]}
		sed -e "s/\[\[${search}\]\]/${replace}/g" <$output_html_file > index.html.tmp 
		cp index.html.tmp $output_html_file
    done  
		if [ -f "index.html.tmp" ]
		then 
		rm index.html.tmp
		fi
else
echo "$input_html_file not found."
fi

echo "Output File Processed"