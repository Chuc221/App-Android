<?php
//kết nối data
require"ketnoi.php";

$id = $_POST['idsp'];
$daban = $_POST['daban'];

$query="UPDATE spmobile SET daban='$daban'WHERE id='$id'";

if(mysqli_query($connect,$query)){
    //thành công
    echo "success";
} else{
    //lỗi
    echo "error";
}

?>