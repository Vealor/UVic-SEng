for i in {0..1}{1..9} 20 ; do
	echo "---------- Checking file $i ----------"
	python ./sengfmt2.py ./outputs/in$i.txt > ./temp/myout$i.txt
	diff ./outputs/out$i.txt ./temp/myout$i.txt
done


echo "----------  CREATING EXTRA  ----------"
python ./sengfmt2extra.py ./outputs/in20extra.txt > ./temp/myout20extra.txt
