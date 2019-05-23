 <?php 

	include 'baglanti.php'; 
	
	$sorgu =$_POST["sorgu"];;
	
	$result = mysqli_query($connect, $sorgu);
	$satir_sayisi = mysqli_num_rows($result);
	
	$dizi  = array();
	 
	if($satir_sayisi > 0) {
		while ($satir = mysqli_fetch_assoc($result)) { 
			$dizi[] = $satir;
		}   
	}
	
	header('Content-Type: application/json');
	echo json_encode(array("kitap"=>$dizi));
	mysqli_close($connect);