<?php
//kết nối data
require"ketnoi.php";

 $iddon = $_POST['iddon'];
//  $iddon = 3;

$query="SELECT spmobile.id,spmobile.ten,spmobile.gianiemyet, spmobile.giaban, spmobile.hinhanh, chitietoder.soluongsp
FROM spmobile, oder,chitietoder 
WHERE oder.id=$iddon AND chitietoder.iddon=oder.id AND spmobile.id=chitietoder.idsp";

$data = mysqli_query($connect,$query);

//tạo class
class DonChiTiet{
    public $id;
    public $ten;
    public $gianiemyet;
    public $giaban;
    public $hinhanh;
    public $soluongsp;
    public function __construct($id, $ten, $gianiemyet, $giaban, $hinhanh, $soluongsp){
        $this->id = $id;
        $this->ten = $ten;
        $this->gianiemyet = $gianiemyet;
        $this->giaban = $giaban;
        $this->hinhanh = $hinhanh;
        $this->soluongsp = $soluongsp;
    }
}

//tạo mảng
$mangDon=array();

//thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data)){
    array_push($mangDon,new DonChiTiet(
        $row['id'], 
        $row['ten'], 
        $row['gianiemyet'],
        $row['giaban'],
        $row['hinhanh'],
        $row['soluongsp']
    ));
}

echo json_encode($mangDon);

?>