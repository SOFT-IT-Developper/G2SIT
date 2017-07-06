package bj.softit.g2sit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Operant.
 */
@Entity
@Table(name = "operant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "operant")
public class Operant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fistname")
    private String fistname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "telephone")
    private Long telephone;

    @OneToMany(mappedBy = "operantst")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(mappedBy = "operantos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OutStock> stockes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFistname() {
        return fistname;
    }

    public Operant fistname(String fistname) {
        this.fistname = fistname;
        return this;
    }

    public void setFistname(String fistname) {
        this.fistname = fistname;
    }

    public String getLastname() {
        return lastname;
    }

    public Operant lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getTelephone() {
        return telephone;
    }

    public Operant telephone(Long telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Operant stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public Operant addStocks(Stock stock) {
        this.stocks.add(stock);
        stock.setOperantst(this);
        return this;
    }

    public Operant removeStocks(Stock stock) {
        this.stocks.remove(stock);
        stock.setOperantst(null);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public Set<OutStock> getStockes() {
        return stockes;
    }

    public Operant stockes(Set<OutStock> outStocks) {
        this.stockes = outStocks;
        return this;
    }

    public Operant addStocke(OutStock outStock) {
        this.stockes.add(outStock);
        outStock.setOperantos(this);
        return this;
    }

    public Operant removeStocke(OutStock outStock) {
        this.stockes.remove(outStock);
        outStock.setOperantos(null);
        return this;
    }

    public void setStockes(Set<OutStock> outStocks) {
        this.stockes = outStocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Operant operant = (Operant) o;
        if (operant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operant{" +
            "id=" + getId() +
            ", fistname='" + getFistname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", telephone='" + getTelephone() + "'" +
            "}";
    }
}
