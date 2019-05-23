 <?php 

	include 'baglanti.php'; 
	
	$kitap = $_POST["id"];
	$query = "SELECT * FROM kitap WHERE id ='$kitap'";
	$result = mysqli_query($connect, $query);
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