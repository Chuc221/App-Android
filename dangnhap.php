<?php
//kết nối data
require"ketnoi.php";

$user = $_POST['username'];
$password = $_POST['password'];


// $user ='admin';
// $password = '123456';

$query="SELECT * FROM nguoidung WHERE username='".$user."' AND password='".$password."'";

$data = mysqli_query($connect,$query);

echo json_encode(mysqli_fetch_assoc($data));

?>