<?php
//kết nối data
require"ketnoi.php";

$query="SELECT * FROM spmobile WHERE idloaidienthoai!=7 ORDER BY id DESC LIMIT 10";

$data = mysqli_query($connect,$query);

//tạo class
class DienThoai{
    public $id;
    public $tenDienThoai;
    public $giaNiemYet;
    public $giaBan;
    public $hinhAnh;
    public $moTa;
    public $daBan;
    public $idLoaiDienThoai;
    public function __construct($id, $tenDienThoai, $giaNiemYet, $giaBan, $hinhAnh, $moTa, $daBan, $idLoaiDienThoai){
        $this->id = $id;
        $this->tenDienThoai = $tenDienThoai;
        $this->giaNiemYet = $giaNiemYet;
        $this->giaBan = $giaBan;
        $this->hinhAnh = $hinhAnh;
        $this->moTa = $moTa;
        $this->daBan = $daBan;
        $this->idLoaiDienThoai = $idLoaiDienThoai;
    }
}

//tạo mảng
$mangSPNew=array();

//thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data)){
    array_push($mangSPNew,new DienThoai(
        $row['id'], 
        $row['ten'], 
        $row['gianiemyet'],
        $row['giaban'],
        $row['hinhanh'],
        $row['mota'],
        $row['daban'],
        $row['idloaidienthoai']
    ));
}

echo json_encode($mangSPNew);

?>