package me.guanjun.service.impl;

import me.guanjun.persistent.dao.BannerDAO;
import me.guanjun.persistent.model.Banner;
import me.guanjun.service.BannerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guanj on 2014/4/28.
 */
public class BannerServiceImpl implements BannerService {
    private BannerDAO bannerDAO;

    public void setBannerDAO(BannerDAO bannerDAO) {
        this.bannerDAO = bannerDAO;
    }

    @Override
    public List getBanners(String title) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("title",title);
        return bannerDAO.queryObjs(condition);
    }

    @Override
    public Banner getBanner(String bid) throws RuntimeException, Exception {
        return bannerDAO.queryObj(bid);
    }

    @Override
    public boolean removeBanner(String bid) throws RuntimeException, Exception {
        bannerDAO.execDelete(bid);
        return true;
    }

    @Override
    public boolean addBanner(Banner banner) throws RuntimeException, Exception {
        bannerDAO.execInsert(banner);
        return true;
    }

    @Override
    public boolean modifyBanner(Banner banner) throws RuntimeException, Exception {
        bannerDAO.execUpdate(banner);
        return true;
    }

    @Override
    public List getBanners(String title, int offset, int limit) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("title",title);
        return bannerDAO.queryObjs(condition,offset,limit);
    }

    @Override
    public int getCountBanners(String title) throws RuntimeException, Exception {
        Map condition = new HashMap();
        condition.put("title",title);
        return bannerDAO.queryCountObjs(condition);
    }
}
