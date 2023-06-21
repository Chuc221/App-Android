<?php
//kết nối data
require"ketnoi.php";

$iddon = $_POST['iddon'];
$idsp = $_POST['idsp'];
$soluong = $_POST['soluong'];


$query="INSERT INTO chitietoder VALUES('$iddon','$idsp','$soluong')";

if(mysqli_query($connect,$query)){
    //thành công
    echo "success";
} else{
    //lỗi
    echo "error";
}
?>