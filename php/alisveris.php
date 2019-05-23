 <?php 

	include 'baglanti.php'; 
	
	$kitap = $_POST["id"]; //gelen kisi variable değerini aldık Spiinerdan sectiğimiz kişi değeri
	
	
	$query = "SELECT * FROM kitap WHERE id ='$kitap' and adet>0";
	$result = mysqli_query($connect, $query);
	$satir_sayisi = mysqli_num_rows($result);
			
	if($satir_sayisi > 0) {
		$query = " UPDATE kitap SET adet=adet-1 WHERE id ='$kitap'";
		$result = mysqli_query($connect, $query);
		$satir_sayisi = mysqli_num_rows($result);
		
		if($result == 1 ){
			$json['success'] = 'Siprişiniz başarıyla tamamlanmıştır.';
		}else{
			$json['error'] = 'Tekrar Deneyiniz.';
		}
	}
	else 
	{
		$json['error'] = 'Ne yazık ki daha hızlı davranan birileri oldu:( Ürün stoklarda kalmadığı için siparişiniz oluşturulamadı.';
	}
	
	echo json_encode($json);
