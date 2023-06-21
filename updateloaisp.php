<?php
//kết nối data
require"ketnoi.php";

$id = $_POST['id'];
$ten = $_POST['ten'];
$url = $_POST['url'];
$query="UPDATE loaihang SET tenloaidienthoai = '$ten', hinhanh = '$url' WHERE id = $id";

if(mysqli_query($connect,$query)){
    //thành công
    echo "success";
} else{
    //lỗi
    echo "error";
}

?>