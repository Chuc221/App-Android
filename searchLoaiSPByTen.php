<?php
//kết nối data
require"ketnoi.php";

$ten = $_POST['ten'];

$query="SELECT * FROM loaihang where tenloaidienthoai like '%$ten%'";

$data = mysqli_query($connect,$query);

//tạo class
class LoaiDienThoai{
    public $id;
    public $tenLoaiDienThoai;
    public $hinhAnh;
    public function __construct($id, $tenLoaiDienThoai, $hinhAnh){
        $this->id = $id;
        $this->tenLoaiDienThoai = $tenLoaiDienThoai;
        $this->hinhAnh = $hinhAnh;
    }
}

//tạo mảng
$mangSP=array();

//thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data)){
    array_push($mangSP,new LoaiDienThoai($row['id'], $row['tenloaidienthoai'], $row['hinhanh']));
}

echo json_encode($mangSP);

?>