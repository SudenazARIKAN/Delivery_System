# Kubernetes Kurulum ve Çalıştırma Rehberi

Bu rehber, oluşturulan Kubernetes dosyalarının nasıl kullanılacağını açıklar.

## Ön Koşullar
- **Docker**: Bilgisayarınızda yüklü olmalı.
- **Kubernetes (Minikube veya Docker Desktop K8s)**: Aktif ve çalışıyor olmalı.
- **kubectl**: Komut satırı aracı yüklü olmalı.

## Adım 1: Docker İmajlarını Oluşturma
Kubernetes'in bu imajları bulabilmesi için projelerinizin Docker imajlarını build etmelisiniz. Eğer Minikube kullanıyorsanız, önce docker env'i minikube'e göre ayarlayın: `minikube docker-env | Invoke-Expression` (PowerShell için).

```powershell
# Ana dizinde:
docker build -t auth-service:latest ./auth-service
docker build -t shipment-service:latest ./shipment-service
docker build -t courier-service:latest ./courier-service
docker build -t notification-service:latest ./notification-service
docker build -t gateway-service:latest ./gateway-service
```

## Adım 2: Altyapıyı Kurma (Veritabanları ve Kafka)
Önce veritabanlarını ve mesajlaşma altyapısını başlatın.

```powershell
kubectl apply -f k8s/infrastructure/
```

Podların çalışır duruma gelmesini bekleyin (`Running` statüsü):
```powershell
kubectl get pods
```

## Adım 3: Mikroservisleri Başlatma
Diğer servisleri başlatın.

```powershell
kubectl apply -f k8s/services/
```

## Adım 4: Erişim
API Gateway üzerinden sisteminize erişebilirsiniz.

- **Docker Desktop**: `http://localhost:8080`
- **Minikube**: `minikube service api-gateway --url` komutu ile adresi alabilirsiniz veya `minikube tunnel` çalıştırıp `localhost:8080` kullanabilirsiniz.

## Sistem Durumu
Tüm sistemi kontrol etmek için:
```powershell
kubectl get all
```

## Temizleme (Silme)
Tüm kurulumu kaldırmak için:
```powershell
kubectl delete -f k8s/services/
kubectl delete -f k8s/infrastructure/
```
