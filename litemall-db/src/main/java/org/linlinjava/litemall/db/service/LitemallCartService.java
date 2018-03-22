package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallCartMapper;
import org.linlinjava.litemall.db.domain.LitemallCart;
import org.linlinjava.litemall.db.domain.LitemallCartExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallCartService {
    @Resource
    private LitemallCartMapper cartMapper;

    public LitemallCart queryExist(Integer goodsId, Integer productId, Integer userId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId).andUserIdEqualTo(userId);
        return cartMapper.selectOneByExample(example);
    }

    public void add(LitemallCart cart) {
        cartMapper.insertSelective(cart);
    }

    public void update(LitemallCart cart) {
        cartMapper.updateByPrimaryKey(cart);
    }

    public List<LitemallCart> queryByUid(int userId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId);
        return cartMapper.selectByExample(example);
    }


    public List<LitemallCart> queryByUidAndChecked(Integer userId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true);
        return cartMapper.selectByExample(example);
    }

    public List<LitemallCart> queryByUidAndSid(int userId, String sessionId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId);
        return cartMapper.selectByExample(example);
    }

    public int delete(List<Integer> productIdList, int userId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(productIdList);
        return cartMapper.deleteByExample(example);
    }

    public LitemallCart findById(Integer id) {
        return cartMapper.selectByPrimaryKey(id);
    }

    public int updateCheck(Integer userId, List<Integer> idsList, Boolean checked) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(idsList);
        LitemallCart cart = new LitemallCart();
        cart.setChecked(checked);
        return cartMapper.updateByExampleSelective(cart, example);
    }

    public void clearGoods(Integer userId) {
        LitemallCartExample example = new LitemallCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true);
        cartMapper.deleteByExample(example);
    }

    public List<LitemallCart> querySelective(Integer userId, Integer goodsId, Integer page, Integer limit, String sort, String order) {
        LitemallCartExample example = new LitemallCartExample();
        LitemallCartExample.Criteria criteria = example.createCriteria();

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        PageHelper.startPage(page, limit);
        return cartMapper.selectByExample(example);
    }

    public int countSelective(Integer userId, Integer goodsId, Integer page, Integer limit, String sort, String order) {
        LitemallCartExample example = new LitemallCartExample();
        LitemallCartExample.Criteria criteria = example.createCriteria();

        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        if(goodsId != null){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        return (int)cartMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        cartMapper.deleteByPrimaryKey(id);
    }
}
