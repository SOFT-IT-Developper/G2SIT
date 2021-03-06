package bj.softit.g2sit.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Entity Historiques
 */
@ApiModel(description = "Entity Historiques")
@Entity
@Table(name = "historiques")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "historiques")
public class Historiques implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation")
    private String operation;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Stock stocks;

    @ManyToOne
    private OutStock outs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public Historiques operation(String operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Historiques date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public Historiques user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stock getStocks() {
        return stocks;
    }

    public Historiques stocks(Stock stock) {
        this.stocks = stock;
        return this;
    }

    public void setStocks(Stock stock) {
        this.stocks = stock;
    }

    public OutStock getOuts() {
        return outs;
    }

    public Historiques outs(OutStock outStock) {
        this.outs = outStock;
        return this;
    }

    public void setOuts(OutStock outStock) {
        this.outs = outStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Historiques historiques = (Historiques) o;
        if (historiques.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historiques.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Historiques{" +
            "id=" + getId() +
            ", operation='" + getOperation() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
