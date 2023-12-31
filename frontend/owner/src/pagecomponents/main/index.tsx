import { useEffect, useState } from 'react';
import { MainContainer, OperButtonList, OperButton, OperText, Menu, MenuList } from './Main.styled';
import { useRouter } from 'next/navigation';
import useModal from '@/hooks/common/modal.hook';
import StartPopup from '@/components/main/startpopup';
import CloseAPI from '@/apis/close/CloseAPI';
import useDateFlagHook from '@/hooks/apis/dateflag.hook';
import useFindCurrentLoc from '@/hooks/common/findcurrentloc.hook';
import SelectFlag from '@/components/main/seletflag';
import SelectFlagAPI from '@/apis/flag/SelectFlagAPI';
import kakaomapApi from '@/apis/kakao/kakaoAPI';
import OwnerInfoStore from '@/stores/ownerinfo/ownerInfoStore';
import useSetOwnerInfoHook from '@/hooks/owner/ownerInfo.hook';

const MainPage = () => {
  const router = useRouter();
  const { state } = OwnerInfoStore();
  const setOwner = useSetOwnerInfoHook();

  const [isVendorOpen, setVendorOpen] = useState(false);

  useEffect(() => {
    if (state === 'OPEN') {
      setVendorOpen(true);
    } else {
      setVendorOpen(false);
    }
  }, [state]);

  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken');
    setOwner(accessToken);
  }, []);

  const today = new Date().toISOString().split('T')[0];
  const todayflag = useDateFlagHook(today); // 깃발조회

  const { isModalOpen, openModal, closeModal } = useModal();
  const [modalContent, setModalContent] = useState<React.ReactNode>(null);

  const handleStartOperation = () => {
    setVendorOpen(true);
    setModalContent(<StartPopup onClose={closeModal} />);
  };

  // 현재위치 가져오기
  const [addressName, setAddressName] = useState('');
  const { position } = useFindCurrentLoc(setAddressName);

  const handleBackFromSelectFlag = () => {
    setVendorOpen(false);
  };

  const switchVendor = async () => {
    if (!isVendorOpen) {
      const addressDetails = await kakaomapApi({ latitude: position.lat, longitude: position.lng });

      if (todayflag.length == 0) {
        const addressParts = addressDetails.split(' ');
        const data = {
          flagId: 0,
          lati: position.lat,
          longi: position.lng,
          activeArea: addressName,
          region1: addressParts[0] || '부산광역시',
          region2: addressParts[1] || '강서구',
          region3: addressParts[2] || '송정동',
          region4: addressParts[3] || '',
        };
        const response = await SelectFlagAPI(data);

        setModalContent(<StartPopup onClose={closeModal} />);
      } else {
        setModalContent(
          <SelectFlag flags={todayflag} onStartOperation={handleStartOperation} onClose={closeModal} onBack={handleBackFromSelectFlag} />,
        );
      }
      openModal(); // 모달 열기
    } else {
    }
    setVendorOpen(!isVendorOpen);
  };

  const handleCloseAPI = async () => {
    try {
      const res = await CloseAPI();
      if (res) {
        router.push('/close');
      }
    } catch (error) {}
  };

  return (
    <MainContainer>
      <OperButtonList>
        <OperButton>{isVendorOpen ? <OperText>영업 중</OperText> : <OperText>영업 전</OperText>}</OperButton>
        <OperButton type="btn" onClick={!isVendorOpen ? switchVendor : handleCloseAPI}>
          {!isVendorOpen ? <OperText>영업 시작</OperText> : <OperText>영업 종료</OperText>}
        </OperButton>
      </OperButtonList>
      <MenuList>
        <Menu>
          <img src="/images/main/vendor.png" style={{ width: '75px' }} />
          <div>가게 정보 관리</div>
        </Menu>
        <Menu
          onClick={() => {
            router.push('/item');
          }}
        >
          <img src="/images/main/item.png" style={{ width: '75px' }} />
          <div>상품 관리</div>
        </Menu>
        <Menu
          onClick={() => {
            router.push('/category');
          }}
        >
          <img src="/images/main/category.png" style={{ width: '75px' }} />
          <div>카테고리 설정</div>
        </Menu>
        <Menu
          onClick={() => {
            router.push('/flag');
          }}
        >
          <img src="/images/common/flag.png" style={{ width: '75px' }} />
          <div>깃발 관리</div>
        </Menu>
        <Menu
          onClick={() => {
            router.push('/orderlist');
          }}
        >
          <img src="/images/main/list.png" style={{ width: '75px' }} />
          <div>주문 목록</div>
        </Menu>
        <Menu
          onClick={() => {
            router.push('/wishlist');
          }}
        >
          <img src="/images/main/graph.png" style={{ width: '75px' }} />
          <div>지역 통계</div>
        </Menu>
      </MenuList>
      {isModalOpen && modalContent}
    </MainContainer>
  );
};

export default MainPage;
