<?php
//kết nối data
require"ketnoi.php";

$query = "SELECT * FROM hinhanhqc";

$data = mysqli_query($connect,$query);

//tạo class
class LoaiSanPham{
    public $id;
    public $hinhanhquangcao;
    public function __construct($id, $hinhanhquangcao){
        $this->id = $id;
        $this->hinhanhquangcao = $hinhanhquangcao;
    }
}

//tạo mảng
$mangQC=array();

//thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data)){
    array_push($mangQC,new LoaiSanPham($row['id'], $row['hinhanhquangcao']));
}

echo json_encode($mangQC);

?>