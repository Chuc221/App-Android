<?php
//kết nối data
require"ketnoi.php";

$id = $_POST['iddon'];
$tt = $_POST['tt'];
$query="UPDATE oder SET trangthai=$tt WHERE id='$id'";

if(mysqli_query($connect,$query)){
    //thành công
    echo "success";
} else{
    //lỗi
    echo "error";
}

?>