for i in 01 02 03 04 05 06 07 09 10 11 12 13 14 15 16 17 18 19 20; do
	echo "---------- Checking file $i ----------"
	./sengfmt3 ./outputs/in$i.txt > ./temp/myout$i.txt
	diff ./outputs/out$i.txt ./temp/myout$i.txt
done


#echo "----------  CREATING EXTRA  ----------"
#python ./sengfmt2extra.py ./outputs/in20extra.txt > ./temp/myout20extra.txt
