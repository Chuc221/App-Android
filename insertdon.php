<?php
//kết nối data
require"ketnoi.php";

$idkh = $_POST['idkh'];
$hoten = $_POST['hoten'];
$sodt = $_POST['sodt'];
$diachi = $_POST['diachi'];
$soluongsp = $_POST['soluongsp'];
$tongthanhtoan = $_POST['tongthanhtoan'];
// $idkh = 1;
// $hoten = 'test';
// $sodt = '05195';
// $diachi = 'diachi';
// $soluongsp = 2;
// $tongthanhtoan = 1000;
$trangthai = 1;

$query="INSERT INTO oder VALUES(null,'$idkh','$hoten','$sodt','$diachi','$soluongsp','$tongthanhtoan','$trangthai' )";

if(mysqli_query($connect,$query)){
    
    $query1="SELECT id FROM oder WHERE idkh='$idkh' ORDER BY id DESC LIMIT 1";

    $data = mysqli_query($connect,$query1);

echo json_encode(mysqli_fetch_assoc($data));
} else{
    //lỗi
    echo "error";
}

?>