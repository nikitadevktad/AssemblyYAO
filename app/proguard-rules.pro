# Gson DTO fields use @SerializedName so their JSON names survive R8 optimization.
# Retrofit keeps service metadata through its published consumer rules.
-keepattributes Signature,InnerClasses,EnclosingMethod
