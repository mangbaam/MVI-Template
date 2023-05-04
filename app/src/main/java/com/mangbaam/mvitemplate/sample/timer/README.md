# Sample - 타이머

## 동작

### 상태 관리

https://user-images.githubusercontent.com/44221447/236191408-a2f61cbd-b41b-4509-8f47-21922203b924.mov

- 시작, 일시중단, 중지 버튼 클릭 이벤트 처리
- 상태에 따른 색상 처리
- 10초 경과 후 토스트 노출 (사이드이펙트)
- 일시중단 시 스낵바 노출 (사이드이펙트)

### `SavedStateHandle` 활용한 상태 유지

https://user-images.githubusercontent.com/44221447/236191415-3102dff7-0c34-4ac0-b1b8-545116887797.mov

- `SaveableMutableStateFlow` 활용한 상태 저장
- adb 명령으로 프로세스 킬
  - 시스템에 의한 프로세스 종료 현상 재현
  - ViewModel 객체도 소멸됨
- 상태 유지 확인
