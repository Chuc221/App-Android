<?php
//kết nối data
require"ketnoi.php";

$username = $_POST['username'];
$password = $_POST['password'];
$hoten = $_POST['hoten'];
$diachi = $_POST['diachi'];
$sodt = $_POST['sodt'];

$query="INSERT INTO nguoidung VALUES(null,'$username','$password','$hoten','$diachi','$sodt')";

if(mysqli_query($connect,$query)){
    //thành công
    echo "success";
} else{
    //lỗi
    echo "error";
}
?>