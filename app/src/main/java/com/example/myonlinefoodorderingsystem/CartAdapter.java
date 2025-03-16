package com.example.myonlinefoodorderingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;  // ✅ Added context
    private List<CartItem> cartItems;

    // ✅ Update constructor to accept Context
    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        }

        CartItem cartItem = cartItems.get(position);

        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemPrice = convertView.findViewById(R.id.itemPrice);
        TextView itemQuantity = convertView.findViewById(R.id.itemQuantity);

        itemName.setText(cartItem.getItem().getName());
        itemPrice.setText("$" + String.format("%.2f", cartItem.getItem().getPrice()));
        itemQuantity.setText("x" + cartItem.getQuantity());

        return convertView;
    }
}
