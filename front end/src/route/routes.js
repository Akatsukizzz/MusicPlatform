import Home from "../page/Home";
import MyMusic from "../page/MyMusic";
import Friend from "../page/Friend";
import Shop from "../page/Shop";
import Musician from "../page/Musician";
import Download from "../page/Download";
import NotFound from "../page/NotFound";
import NewSong from "../page/Home/NewSong";
import Radio from "../page/Home/MusicRadio";
import RanList from "../page/Home/RankList";
import Songer from "../page/Home/Songer";
import SongList from "../page/Home/SongList";
import Recommend from "../page/Home/Recommend";


const routes = [
    {
        //主页
        path: '/home',
        component: Home,
        children: [
            {
              path:'/home/recommend',
              component: Recommend,
              exact: true
            },
            {
                path: 'home/ranklist',
                component: RanList,
                exact: true
            },
            {
                path: 'home/songlist',
                component: SongList,
                exact: true
            },
            {
                path: 'home/musicradio',
                component: Radio,
                exact: true
            },
            {
                path: 'home/songer',
                component: Songer,
                exact: true
            },
            {
                path: 'home/newsong',
                component: NewSong,
                exact: true
            }
        ]
    },
    {
        // 我的音乐
        path: '/mymusic',
        component: MyMusic,
        exact: true
    },
    {
        //朋友
        path: '/friend',
        component: Friend,
        exact: true
    },
    {
        //商城
        path: '/shop',
        component: Shop,
        exact: true
    },
    {
        //音乐人
        path: '/musician',
        component: Musician,
        exact: true
    },
    {
        //下载客户端
        path: '/download',
        component: Download,
        exact: true
    },
    {
        //404页面
        path: '',
        component: NotFound,
    }
]

export default routes;
