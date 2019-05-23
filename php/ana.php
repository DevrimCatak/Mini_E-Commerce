 <?php 

	include 'baglanti.php'; 
	
	$query = " SELECT * FROM ana";
	$result = mysqli_query($connect, $query);
	$satir_sayisi = mysqli_num_rows($result);
	
	$dizi  = array();
	 
	if($satir_sayisi > 0) {
		while ($satir = mysqli_fetch_assoc($result)) { 
			$dizi[] = $satir;
		}   
	}
	
	header('Content-Type: application/json');
	echo json_encode(array("ana"=>$dizi));
	mysqli_close($connect);