#!/bin/bash

FILES=images/*.jpg

for f in $FILES
do
  echo "Processing file $f..."

#  filename=$(basename "$f")
  filename=$f

  filename="${filename%.*}"
  pgm=$filename
  pgm+='.pgm'
  ppm=$filename
  ppm+='.ppm'

  convert $f $pgm
  echo 'pgm [OK]'

  convert $f $ppm
  echo "ppm [OK]"

done