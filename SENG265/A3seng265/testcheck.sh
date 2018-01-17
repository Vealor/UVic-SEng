for i in 01 {0..1}{2..9} 20 ; do
	echo "---------- Checking file $i ----------"
	python ./sengfmt.py ./outputs/in$i.txt > ./temp/myout$i.txt
	diff ./outputs/out$i.txt ./temp/myout$i.txt
done
