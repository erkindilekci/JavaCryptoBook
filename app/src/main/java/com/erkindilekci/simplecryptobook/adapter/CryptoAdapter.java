package com.erkindilekci.simplecryptobook.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erkindilekci.simplecryptobook.databinding.RecyclerRowBinding;
import com.erkindilekci.simplecryptobook.model.Crypto;

import java.util.List;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder> {

    private List<Crypto> cryptos;

    public CryptoAdapter(List<Crypto> cryptos) {
        this.cryptos = cryptos;
    }

    public class CryptoViewHolder extends RecyclerView.ViewHolder {
        RecyclerRowBinding binding;

        public CryptoViewHolder(RecyclerRowBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CryptoViewHolder(RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {
        Crypto current = cryptos.get(position);

        holder.binding.recyclerViewNameText.setText(current.name());
        holder.binding.recyclerViewPriceText.setText("$" + String.format("%.3f", current.price()));
    }

    @Override
    public int getItemCount() {
        return cryptos.size();
    }
}
