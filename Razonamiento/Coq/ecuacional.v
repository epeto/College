Require Import Utf8.
(* Razonamiento Automatizado 2020-2
   Selene Linares
   Razonamiento Ecuacional *)


Hypotheses (A:Set)
           (e:A)  (* Elemento identidad *)
           (m:A → A → A)  (* Operacion del grupo mul x y = x * y *)
           (i f g:A → A). (* Inverso i x = x^-1*)

Infix "&" := m (at level 40).

Axiom asoc : ∀ x y z : A, x & (y & z) = (x & y) & z. (* x*(y* z)=(x*y)*z *)

Axiom idI : ∀ x:A, e & x = x.  (* e*x = x *)

Axiom invI : ∀ x:A, (i x) & x = e.  (* x^-1 * x = e *)

Theorem Ej1: (∀ y:A, f(g(y)) = y) → (∀ w:A, g(g(w))=w )→ (∀ x:A,f(x) = g(x)).
Proof.
intros.
transitivity (f(g(g(x)))).
symmetry.
- f_equal. apply H0.
- apply (H (g(x))).
Qed.


Lemma congI: ∀ x y z:A, x = y → x & z = y & z.
Proof.
intros.
rewrite H.
reflexivity.
Qed.


Lemma congD: ∀ x y z:A, x = y → z & x = z & y.
Proof.
intros.
rewrite H.
reflexivity.
Qed.

Theorem ej1: ∀ x y:A,  (x & y) & (e & x) = x & (y & x).
Proof.
intros.
rewrite idI.
rewrite <- asoc.
reflexivity.
Qed.


Theorem UnicIdI: ∀ x:A, x & x = x → x = e.
Proof.
intros.
rewrite <- idI with x.
rewrite <- (invI x).
rewrite <- asoc.
rewrite H.
reflexivity.
Qed.

Theorem CanI: ∀ x y z:A, x & y = x & z → y = z.
Proof.
intros.
rewrite <- (idI y).
rewrite <- (idI z).
rewrite <- (invI x).
rewrite <- asoc.
rewrite <- asoc.
congruence.
Qed.