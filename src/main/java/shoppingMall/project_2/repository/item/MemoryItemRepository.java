package shoppingMall.project_2.repository.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import shoppingMall.project_2.domain.item.Item;
import shoppingMall.project_2.domain.item.ItemSearchCond;
import shoppingMall.project_2.domain.item.ItemUpdateForm;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class MemoryItemRepository implements ItemRepository{

    private final NamedParameterJdbcTemplate template;

    public MemoryItemRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);


    }

    @Override
    public Item addItem(Item item) {
        String sql = "insert into item(quantity,price,item_name,user_id,like_count,image) values(:quantity,:price,:itemName,:userId,:likeCount,:image)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(item);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        long key = keyHolder.getKey().longValue();
        item.setId(key);
        return item;

    }

    @Override
    public void update(Long id, ItemUpdateForm updateForm) {
        String sql = "update item set item_name=:itemName, price=:price, quantity=:quantity, image=:image where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", updateForm.getItemName())
                .addValue("price", updateForm.getPrice())
                .addValue("quantity", updateForm.getQuantity())
                .addValue("image", updateForm.getImage())
                .addValue("id", id);

        template.update(sql, param);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id,quantity,price,item_name,user_id,like_count,image from item where id=:id";
        try {
            Map<String, Object> param = Map.of("id", id);
            Item item = template.queryForObject(sql, param, ItemRowMapper());
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    private RowMapper<Item> ItemRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class);
    }


    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        Integer maxPrice = cond.getMaxPrice();
        String itemName = cond.getItemName();
        String sql = "select id,quantity,price,item_name,user_id,like_count,image from item";
        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql+=" where";
        }
        boolean andFlag=false;

        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',:itemName,'%')";
            andFlag=true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " price <=:maxPrice";
        }

        return template.query(sql, param, ItemRowMapper());
    }
}
