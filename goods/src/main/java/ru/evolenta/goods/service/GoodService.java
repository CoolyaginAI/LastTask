package ru.evolenta.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.evolenta.goods.model.Good;
import ru.evolenta.goods.repository.GoodRepository;

import java.util.Optional;

@Service
public class GoodService {

    @Autowired
    private GoodRepository goodRepository;

    public Iterable<Good> getGood() {
        return goodRepository.findAll();
    }

    public Optional<Good> getGoodById(int idGood) {
        return goodRepository.findById(idGood);
    }

    public Good addGood(Good good) {
        goodRepository.save(good);
        return good;
    }

    public boolean existGoodById(int idGood) {
        return goodRepository.existsById(idGood);
    }

    public void updateGood(int id, Good good) {
        goodRepository.findById(id).get().setNameGood(good.getNameGood());
        goodRepository.findById(id).get().setDescriptionGood(good.getDescriptionGood());
        goodRepository.findById(id).get().setCost(good.getCost());
        goodRepository.findById(id).get().setAmount(good.getAmount());
    }

    public void deleteGoodById(int id) {
        goodRepository.deleteById(id);
    }

}
