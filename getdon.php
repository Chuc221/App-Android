<?php
//kết nối data
require"ketnoi.php";

 $idkh = $_POST['idkh'];
 $tt = $_POST['tt'];

$query="SELECT * FROM oder WHERE idkh=$idkh AND trangthai=$tt ORDER BY id DESC";

$data = mysqli_query($connect,$query);

//tạo class
class Don{
    public $id;
    public $idkh;
    public $hoten;
    public $sodt;
    public $diachi;
    public $soluongsp;
    public $tongthanhtoan;
    public function __construct($id, $idkh, $hoten, $sodt, $diachi, $soluongsp, $tongthanhtoan){
        $this->id = $id;
        $this->idkh = $idkh;
        $this->hoten = $hoten;
        $this->sodt = $sodt;
        $this->diachi = $diachi;
        $this->soluongsp = $soluongsp;
        $this->tongthanhtoan = $tongthanhtoan;
    }
}

//tạo mảng
$mangDon=array();

//thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data)){
    array_push($mangDon,new Don(
        $row['id'], 
        $row['idkh'], 
        $row['hoten'],
        $row['sodt'],
        $row['diachi'],
        $row['soluongsp'],
        $row['tongthanhtoan']
    ));
}

echo json_encode($mangDon);

?>