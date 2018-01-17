int Mystery(int n) {
	int v1, v2, v3, k;
	if ( n <= 1 ) return 1;
	v1 = v2 = 1;
	v3 = v1 + v2;
	for ( k = n; k > 2; k-- ) {
		v1 = v2;
		v2 = v3;
		v3 = v1 + v2;
	}
	return v3;
}
