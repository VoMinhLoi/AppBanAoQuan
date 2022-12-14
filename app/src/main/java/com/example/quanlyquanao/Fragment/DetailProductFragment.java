package com.example.quanlyquanao.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlyquanao.Activity.MainActivity;
import com.example.quanlyquanao.Class.Product;
import com.example.quanlyquanao.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class DetailProductFragment extends Fragment {

    // region Variable

    private DecimalFormat format = new DecimalFormat("###,###,###");

    // Kiểm tra Product đã được thêm vào cart chưa
    private Boolean isAddToCart;

    // Activity
    private MainActivity mainActivity;

    // View
    private View mView;
    private Product detailProduct;
    private List<Product> listCartProduct;
    private TextView tvDetailProductName,tvDetailProductPrice,tvDetailProductDescription;
    private Button btnDetailProductBuy;
    private ImageView imgDetailProductPhoto;

    // endregion Variable

    public DetailProductFragment(Product product,List<Product> listProduct) {
        detailProduct = product;
        listCartProduct = listProduct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail_product, container, false);

        initItem();

        setValueItem();

        return mView;
    }

    // region Private menthod

    // Khởi tạo các item
    private void initItem(){

        isAddToCart = false;
        mainActivity = (MainActivity) getActivity();
        tvDetailProductName = mView.findViewById(R.id.tv_detail_product_name);
        tvDetailProductPrice = mView.findViewById(R.id.tv_detail_product_price);
        tvDetailProductDescription = mView.findViewById(R.id.tv_detail_product_description);
        imgDetailProductPhoto = mView.findViewById(R.id.img_detail_product_photo);
        btnDetailProductBuy = mView.findViewById(R.id.btn_detail_product_buy);

        if(listCartProduct == null){
            listCartProduct = new ArrayList<>();
        }
    }

    // set giá trị cho các item
    private void setValueItem(){
        if (detailProduct != null){
            tvDetailProductName.setText(detailProduct.getProductName());
            tvDetailProductPrice.setText(format.format(detailProduct.getProductPrice() ) + " VNĐ");
            Glide.with(getContext()).load(detailProduct.getUrlImg()).into(imgDetailProductPhoto);
            tvDetailProductDescription.setText(detailProduct.getDescription());

            // Kiểm tra sản phẩm đã dc add vào giỏ hay chưa
            for (int i = 0;i< listCartProduct.size();i++){

                // Nếu sản  phẩm đã dc add
                if (listCartProduct.get(i).getProductName().equals(detailProduct.getProductName())){
                    isAddToCart = true;
                    btnDetailProductBuy.setText("Đã Mua");
                    btnDetailProductBuy.setBackgroundResource(R.drawable.custom_button_gray);
                    break;
                }
            }

            btnDetailProductBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAddToCart){
                        Toast.makeText(getActivity(),"Sản Phẩm này đã tồn tại trong giỏ hàng",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        isAddToCart = true;
                        btnDetailProductBuy.setText("Đã Mua");
                        btnDetailProductBuy.setBackgroundResource(R.drawable.custom_button_gray);
                        mainActivity.addToListCartProdct(detailProduct);
                        mainActivity.setCountProductInCart(mainActivity.getCountProduct() + 1);
                        Toast.makeText(getActivity(),"Đã thêm sản phẩm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}