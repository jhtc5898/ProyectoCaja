package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cuenta.class)
public abstract class Cuenta_ {

	public static volatile SingularAttribute<Cuenta, Double> saldo_cuenta;
	public static volatile SingularAttribute<Cuenta, Tipo_Cuenta> tipo_cuenta;
	public static volatile SingularAttribute<Cuenta, String> correo_cuenta;
	public static volatile SingularAttribute<Cuenta, String> pswd_cuenta;
	public static volatile SingularAttribute<Cuenta, String> numero_cuenta;
	public static volatile SingularAttribute<Cuenta, Usuario> usuario;
	public static volatile SingularAttribute<Cuenta, Integer> codigo_cuenta;

}

