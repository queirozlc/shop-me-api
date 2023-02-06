package com.lucas.shopme.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "category", schema = "shopme")
public class Category implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(unique = true)
	private String name;
	@Column(unique = true)
	private String alias;
	private boolean enabled;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parent;
	@ToString.Exclude
	@OneToMany(mappedBy = "parent")
	private List<Category> children;
}