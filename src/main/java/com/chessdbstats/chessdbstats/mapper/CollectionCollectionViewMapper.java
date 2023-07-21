package com.chessdbstats.chessdbstats.mapper;

import com.chessdbstats.chessdbstats.controller.CollectionView;
import com.chessdbstats.chessdbstats.model.Collection;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CollectionCollectionViewMapper implements Function<Collection, CollectionView> {

    @Override
    public CollectionView apply(Collection collection) {
        String pgnPath = collection.getPgnPath();

        // Extracting the timestamp part
        String[] parts = pgnPath.split("_");
        String timestampPart = parts[1];

        // Extracting the date part
        String datePart = timestampPart.substring(6, 8) + "." + timestampPart.substring(4, 6) + "." + timestampPart.substring(0, 4);

        CollectionView cv = new CollectionView(collection.getId(),collection.getTitle(),collection.getDescription(),datePart);

        return cv;
    }
}
