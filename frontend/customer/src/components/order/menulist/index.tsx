import MenuCard from '../menucard';
import { BoxContainer } from './Menulist.styled';
import useOrderStore from '@/stores/orderStore';
import { useNavStore } from '@/stores/curnavStore';
import { useRouter } from 'next/navigation';
import BottomBtn from '@/components/common/bottombtn';
const Menulist = ({ vendor }: any) => {
  const menulist = vendor.menuInfoResponseList || [];

  const { order, removeItem } = useOrderStore();
  const isOrderNotEmpty = order.length > 0 && order.some((menu) => menu.quantity > 0);

  const router = useRouter();
  const { curnav } = useNavStore();

  const handleButtonClick = () => {
    order.forEach((menu) => {
      if (menu.quantity === 0) {
        removeItem(menu.menuId);
      }
    });

    const updatedOrder = useOrderStore.getState().order;

    const isOrderValid = updatedOrder.some((menu) => menu.quantity > 0);

    if (isOrderValid) {
      router.push('/topurchase');
    } else {
      console.error('Order is not valid');
    }
  };

  return (
    <BoxContainer>
      {menulist.map((menu: any) => (
        <MenuCard key={menu.id} menuid={menu.id} menulist={menulist} />
      ))}
      <div
        onClick={() => {
          handleButtonClick();
        }}
      >
        {isOrderNotEmpty && <BottomBtn text={curnav == 1 ? '주문 하기' : '펀딩 하기'} />}
      </div>
    </BoxContainer>
  );
};

export default Menulist;
