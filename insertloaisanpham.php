<?php
//kết nối data
require"ketnoi.php";

$ten = $_POST['ten'];
$url = $_POST['url'];

$query="INSERT INTO loaihang VALUES(null,'$ten','$url')";

if(mysqli_query($connect,$query)){
    //thành công
    echo "success";
} else{
    //lỗi
    echo "error";
}
?>