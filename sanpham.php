<?php
//kết nối data
require"ketnoi.php";

$idldt = $_POST['idloaidienthoai'];

$query="SELECT * FROM spmobile WHERE idloaidienthoai=$idldt ORDER BY id DESC";

$data = mysqli_query($connect,$query);

//tạo class
class SanPham{
    public $idSP;
    public $tenSP;
    public $giaNiemYetSP;
    public $giaBanSP;
    public $hinhAnhSP;
    public $moTaSP;
    public $daBanSP;
    public $idLoaiSP;
    public function __construct($idSP, $tenSP, $giaNiemYetSP, $giaBanSP, $hinhAnhSP, $moTaSP, $daBanSP, $idLoaiSP){
        $this->idSP = $idSP;
        $this->tenSP = $tenSP;
        $this->giaNiemYetSP = $giaNiemYetSP;
        $this->giaBanSP = $giaBanSP;
        $this->hinhAnhSP = $hinhAnhSP;
        $this->moTaSP = $moTaSP;
        $this->daBanSP = $daBanSP;
        $this->idLoaiSP = $idLoaiSP;
    }
}

//tạo mảng
$mangSP=array();

//thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data)){
    array_push($mangSP,new SanPham(
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

echo json_encode($mangSP);

?>