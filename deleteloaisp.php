<?php
//kết nối data
require"ketnoi.php";

$id = $_POST['id'];
$query="DELETE FROM loaihang WHERE id = $id";

if(mysqli_query($connect,$query)){
    //thành công
    echo "success";
} else{
    //lỗi
    echo "error";
}

?>